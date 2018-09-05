create table product(
  id bigserial primary key,
  name varchar,
  description varchar,
  price double precision,
  stock integer
);

CREATE FUNCTION insertProduct(name varchar, description varchar, price double precision, stock integer)
  RETURNS bigint AS
  $BODY$
      DECLARE
      p_id bigint;
      BEGIN
        INSERT INTO product(name, description, price, stock)
        VALUES(name, description, price, stock)
        returning id into p_id;

        return p_id;
      END;
  $BODY$
  LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION getProducts ()
 RETURNS TABLE (
 name VARCHAR,
 description varchar,
 price double precision,
 stock integer,
 id bigint
)
AS $$
BEGIN
 RETURN QUERY SELECT p.name, p.description, p.price, p.stock, p.id FROM product p;
END; $$
LANGUAGE 'plpgsql';