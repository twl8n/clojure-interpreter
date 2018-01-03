
-- I wonder if values keys can have hyphens :postal-code vs :postal_code

-- See the readme for an quick explanation of HugSQL headers. 

-- :name create-address
-- :command :execute
-- :result :affected
-- :doc create the address table
create table address (
        id integer primary key autoincrement,
        street text,
        city text,
        postal_code text
);

-- :name address-index
-- :command :execute
-- :result :affected
-- :doc create the primary index on address table
create index address_ndx1 on address (city,street,postal_code);


-- A :result value of :n or :affected return number of rows affected
-- :name insert-address :! :n
-- :command :execute
-- :result :affected
-- :doc Insert a single address returning affected row count
insert into address (street, city, postal_code)
values (:street, :city, :postal_code)


-- :name insert-address-vector
-- :command :execute
-- :result :affected
-- :doc Insert multiple address recs with :tuple* parameter type. 
insert into address (street, city, postal_code)
values :tuple*:address

-- A :result value of :* or :many returns rows a vector of hash-map.
-- (as a list of hashmap) will be returned
-- :name address-ilike-city :? :raw
-- :doc Get address ilike city
select * from address
where city ilike :city


-- A ":result" value of ":1" specifies a single record
-- (as a hashmap) will be returned
-- :name address-by-id :? :1
-- :doc Get character by id
select * from address
where id = :id
