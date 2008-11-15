select t1.product_id, t2.product_id, t3.data_object_id,
  t2.parent_id, t3.parent_data_object_id, t1.product_name, t1.product_code
 from simple_products t1, products t2, data_objects t3
 where t1.product_id = t2.product_id
  and t1.product_id = t3.data_object_id