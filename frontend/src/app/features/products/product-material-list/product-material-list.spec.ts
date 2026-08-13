import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductMaterialList } from './product-material-list';

describe('ProductMaterialList', () => {
  let component: ProductMaterialList;
  let fixture: ComponentFixture<ProductMaterialList>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProductMaterialList],
    }).compileComponents();

    fixture = TestBed.createComponent(ProductMaterialList);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
