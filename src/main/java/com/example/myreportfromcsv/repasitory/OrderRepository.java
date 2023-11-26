package com.example.myreportfromcsv.repasitory;

import com.example.myreportfromcsv.model.Order;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository <Order, Long>{
}
