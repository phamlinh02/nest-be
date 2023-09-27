package com.example.demo.service.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.CharMatcher;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.*;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@UtilityClass
public class CommonUtils {

    public String cleanUnicode(String unicodeStr) {
        if (unicodeStr != null) {
            // replace windows new lines by unix new lines.
            unicodeStr = unicodeStr.replaceAll("\\r\\n|\\r|\\n", "\n");

            //remove every other control character except new line and tab.
            CharMatcher charsToPreserve = CharMatcher.anyOf("\n\t");
            CharMatcher allButPreserved = charsToPreserve.negate();
            CharMatcher controlCharactersToRemove = CharMatcher.javaIsoControl().and(allButPreserved);

            unicodeStr = controlCharactersToRemove.removeFrom(unicodeStr);

            // TODO check on this util later
//            unicodeStr = handleHTMLEntities(unicodeStr);
        }
        return unicodeStr;
    }

    public List<String> readAllLines(String filepath) {
        List<String> lines = new ArrayList<>();
        BufferedReader br = null;
        FileReader fr = null;
        try {
            fr = new FileReader(filepath);
            br = new BufferedReader(fr);

            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                lines.add(cleanUnicode(sCurrentLine));
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {

            try {
                if (br != null)
                    br.close();

                if (fr != null)
                    fr.close();

            } catch (IOException ex) {
                log.error(ex.getMessage(), ex);
            }
        }
        return lines;
    }

    public void writeAllLines(List<String> lines, String filepath) {
        Writer writer = null;

        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(filepath), "utf-8"));
            String linesStr = lines.stream().map(String::valueOf).collect(Collectors.joining(","));
            writer.write("[" + linesStr + "]");
        } catch (IOException ex) {
            log.error(ex.getMessage(), ex);
        } finally {
            try {
                assert writer != null;
                writer.close();
            } catch (Exception ex) {/*ignore*/
                log.error(ex.getMessage(), ex);
            }
        }
    }

    public void setQueryParams(Query query, Query countQuery, Map<String, Object> params) {
        if (null == params || params.isEmpty()) {
            return;
        }

        params.forEach((k, v) -> {
            query.setParameter(k, v);
            countQuery.setParameter(k, v);
        });
    }

    public void setQueryParams(Query query, Map<String, Object> params) {
        if (null == params || params.isEmpty()) {
            return;
        }

        params.forEach((k, v) -> {
            query.setParameter(k, v);
        });
    }

    private String setOrderQuery(List<String> names, Sort sort) {
        StringBuilder orderQuery = new StringBuilder(" ORDER BY 1=1 ");
        names.forEach(name -> {
            orderQuery.append(", ");
            orderQuery.append(name);
            orderQuery.append(" ");
            orderQuery.append(Objects.requireNonNull(sort.getOrderFor(name)).getDirection().toString());
        });
        return orderQuery.toString();
    }


    public String getQueryProjectType(String alias) {
        StringBuilder query = new StringBuilder();
        query.append("(case ");
        query.append(" when @taskType like 'IMAGE%' then 'IMAGE_TYPE' ");
        query.append(" when @taskType like 'VIDEO%' then 'VIDEO_TYPE' ");
        query.append(" when @taskType like 'AUDIO%' then 'AUDIO_TYPE' ");
        query.append(" when @taskType like 'TEXT%'  then 'TEXT_TYPE' ");
        query.append(" end)");
        return query.toString().replaceAll("@taskType", alias);
    }

    public String formatCurrency(Locale locale, Long amount) {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);
        return currencyFormat.format(amount);
    }

    public String formatDate(Instant date, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern)
                .withZone(ZoneId.systemDefault());
        return formatter.format(date);
    }

    public String formatDate(Instant date, DateTimeFormatter formatter) {
        return formatter.format(date);
    }

    public String formatDate(Instant date, String pattern, ZoneId zone) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern)
                .withZone(zone);
        return formatter.format(date);
    }

    public <T> String objToJsonString(T obj) {
        return ReflectionToStringBuilder.toString(obj, ToStringStyle.JSON_STYLE);
    }

    public List<Map<String, Object>> arrayJsonToList(String jsonArray) throws JsonProcessingException {
        final ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonArray, new TypeReference<List<Map<String, Object>>>() {
        });
    }

    public Map<String, Object> strJsonToMap(String json) throws JsonProcessingException {
        final ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, new TypeReference<Map<String, Object>>() {
        });
    }

    public <T> Page<T> getPageImpl(EntityManager em, String sql, Map<String, Object> params, Pageable pageable,
                                   String resultMapping) {
        return getPageImpl(em, sql, "SELECT Count(*) from (" + sql + ") a ", params, pageable, resultMapping);
    }

    @SuppressWarnings("unchecked")
    public <T> Page<T> getPageImpl(EntityManager em, String sql, String sqlCount, Map<String, Object> params,
                                   Pageable pageable, String resultMapping) {
        Query countQuery = em.createNativeQuery(sqlCount);
        Query query = em.createNativeQuery(sql, resultMapping);
        if (!pageable.isUnpaged()) {
            query.setFirstResult((int) pageable.getOffset()).setMaxResults(pageable.getPageSize());
        }
        if (!DataUtils.isNullOrEmpty(params)) {
            setQueryParams(query, countQuery, params);
        }
        List<T> result = query.getResultList();

        return new PageImpl<>(result, pageable, ((BigInteger) countQuery.getSingleResult()).intValue());
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> getResultList(EntityManager em, String sql, Map<String, Object> params, String resultMapping) {
        Query query = em.createNativeQuery(sql, resultMapping);
        if (!DataUtils.isNullOrEmpty(params)) {
            setQueryParams(query, params);
        }
        return (List<T>) query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public <T> Optional<T> getSingleResult(EntityManager em, String sql, Map<String, Object> params, String resultMapping) {
        Query query = em.createNativeQuery(sql, resultMapping);
        if (!DataUtils.isNullOrEmpty(params)) {
            setQueryParams(query, params);
        }
        return query.getResultStream().findFirst();
    }

    public String setOrderQuery(Sort sort) {
        if (Sort.unsorted() == sort)
            return StringUtils.EMPTY;
        List<String> orders = new ArrayList<>();
        sort.get().forEach(s -> {
            orders.add(s.getProperty());
        });
        return setOrderQuery(orders, sort);
    }

    public <T> List<T> getList(EntityManager em, String sql, String sqlCount,
                               String resultMapping) {
        em.createNativeQuery(sqlCount);
        Query query = em.createNativeQuery(sql, resultMapping);
        List<T> result = query.getResultList();

        return result;

    }

    public <T> List<T> getList(EntityManager em, String sql, String resultMapping) {
        return getList(em, sql, "SELECT Count(*) from (" + sql + ") a ", resultMapping);
    }
}
