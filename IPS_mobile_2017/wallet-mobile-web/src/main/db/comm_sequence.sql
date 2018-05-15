-- 执行以下脚本 start --
DROP TABLE IF EXISTS comm_sequence;  
DROP FUNCTION IF EXISTS seq_currval ;
DROP FUNCTION IF EXISTS seq_nextval;
DROP FUNCTION IF EXISTS seq_setval;
 
-- 建comm_sequence表，指定seq列为无符号大整型，可支持无符号值：0(default)到18446744073709551615（0到2^64–1）。
CREATE TABLE comm_sequence (
     name              VARCHAR(50) NOT NULL,  
         current_value     BIGINT UNSIGNED NOT NULL DEFAULT 0,  
         increment         INT NOT NULL DEFAULT 1,  
         PRIMARY KEY (name)   -- 不允许重复seq的存在。
) ENGINE=InnoDB;  
 
 


 
 --/
CREATE FUNCTION seq_currval(seq_name VARCHAR(50)) 
RETURNS BIGINT
BEGIN
         DECLARE value BIGINT;
         SELECT current_value INTO value
         FROM comm_sequence
         WHERE upper(name) = upper(seq_name); -- 大小写不区分.
         RETURN value;
END;
/
 
 
 --/
CREATE FUNCTION seq_nextval (seq_name VARCHAR(50))  
RETURNS BIGINT  
BEGIN  
         DECLARE value BIGINT;
         UPDATE comm_sequence  
         SET current_value = current_value + increment  
         WHERE upper(name) = upper(seq_name);
         RETURN seq_currval(seq_name);  
END;
/

--/
CREATE FUNCTION seq_setval (seq_name VARCHAR(50), value BIGINT)  
RETURNS BIGINT
BEGIN 
         UPDATE comm_sequence  
         SET current_value = value  
         WHERE upper(name) = upper(seq_name);  
         RETURN seq_currval(seq_name);  
END;
/


-- 执行以下脚本 end --


-- read me --
-- 先执行插入一条数据 【】里内容为备注需删除 ： insert into comm_sequence set name='test2'【单表唯一sequence 名称】 , current_value= 100【起点开始数字，默认0】 , increment = 2 【递增数字，默认1】;

-- select seq_nextval('test2') as 'nextval'; 【获取'test2' 下一个序列】
-- select seq_currval('test2') as 'currval'; 【获取'test2' 当前序列】
-- select seq_setval('test2', 0) as 'reset'; 【重置'test2' 当前序列 ，第二个参数为重置的数值】当然 重置调整 可以直接改表也可以生效
