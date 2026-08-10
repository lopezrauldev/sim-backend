ALTER TABLE product_materials
    ADD CONSTRAINT fk_product_material_product
    FOREIGN KEY (product_id)
    REFERENCES products(id);

ALTER TABLE product_materials
    ADD CONSTRAINT fk_product_material_material
    FOREIGN KEY (material_id)
    REFERENCES materials(id);