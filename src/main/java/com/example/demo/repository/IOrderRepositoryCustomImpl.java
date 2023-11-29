package com.example.demo.repository;

import com.example.demo.service.dto.order.BillDTO;
import com.example.demo.service.util.CommonUtils;
import com.example.demo.service.util.DataUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class IOrderRepositoryCustomImpl implements IOrderRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<BillDTO> getAllOrder(Pageable pageable, BillDTO dto) {

        StringBuilder sql = new StringBuilder("select ")
                .append(" b.id, b.order_date orderDate, b.status, b.description, b.reason_deny reasonDeny, ")
                .append(" (select count(b.id) from bill_detail b1 where b1.order_id = b.id GROUP by b1.order_id) countOrder, ")
                .append(" (SELECT sum(b2.price * b2.quantity) from bill_detail b2 where  b2.order_id = b.id ) sumPriceBill, ")
                .append(" a.email, a.username, a.full_name fullName ")
                .append(" from bill b join bill_detail b3 on b.id = b3.order_id ")
                .append(" join account a on a.id = b.account_id ")
                .append(" where 1=1 ");

        Map<String, Object> params = new HashMap<>();

        if (!DataUtils.isNullObject(dto.getId())) {
            sql.append(" and b.id = :id");
            params.put("id", dto.getId());
        }

        if (!DataUtils.isNullObject(dto.getOrderDate())) {
            sql.append(" and b.order_date like :date");
            params.put("date", new SimpleDateFormat("yyyy-MM-dd").format(dto.getOrderDate()) + "%");
        }

        if(!DataUtils.isNullObject(dto.getStatus())){
            sql.append(" and b.status = :status");
            params.put("status", dto.getStatus());
        }

        if(!DataUtils.isNullObject(dto.getAccountId())){
            sql.append(" and b.account_id = :accountId");
            params.put("accountId", dto.getAccountId());
        }

        if(!DataUtils.isNullObject(dto.getStaffId())){
            sql.append(" and b.account_id = :accountId");
            params.put("accountId", dto.getStaffId());
        }

        sql.append(" group by b.id ");
        Pageable temp = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort().and(Sort.by("b.order_date").descending()));
        sql.append(CommonUtils.setOrderQuery(temp.getSort()));

        return CommonUtils.getPageImpl(em, sql.toString(), params, pageable, "getAllOrder");
    }

}
