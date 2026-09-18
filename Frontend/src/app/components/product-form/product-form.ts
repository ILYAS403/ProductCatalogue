import { Component ,ChangeDetectorRef  } from '@angular/core';
import { RouterLink } from '@angular/router';

import { FormsModule } from '@angular/forms';
import { ProductService } from '../../services/product-service';

@Component({
  selector: 'app-product-form',
  imports: [FormsModule, RouterLink],
  templateUrl: './product-form.html',
  styleUrl: './product-form.css'
})
export class ProductForm {

  product = {
    name: '',
    category: '',
    price: 0
  };

  successMessage = '';
  errorMessage = '';
  isAuthenticated = !!localStorage.getItem('token');
  constructor(private productService: ProductService, private cdr: ChangeDetectorRef) {}

  addProduct(): void {


  if (!this.product.name || !this.product.category || this.product.price <= 0) {
    this.errorMessage = 'Please fill in all fields correctly.';
    this.successMessage = '';
    return;
  }

  this.productService.addProduct(this.product).subscribe({
      next: () => {
  
  this.successMessage = 'Product added successfully!';
  this.cdr.detectChanges();
  this.errorMessage = '';
  this.product = {
    name: '',
    category: '',
    price: 0
  };
},
    error: (error) => {
      console.error('ERROR:', error);
      this.errorMessage = 'Unable to add the product please login first.';
      this.successMessage = '';
      this.cdr.detectChanges();
    }
  });
}
}