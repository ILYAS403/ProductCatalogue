import { Component, OnInit , ChangeDetectorRef} from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Product } from '../../models/product';
import { ProductService } from '../../services/product-service';

@Component({
  selector: 'app-product-list',
  imports: [FormsModule],
  templateUrl: './product-list.html',
  styleUrl: './product-list.css'
})
export class ProductList implements OnInit {

  products: Product[] = [];
  searchQuery: string = '';

  constructor(private productService: ProductService, private changeDetectorRef: ChangeDetectorRef) {}

  ngOnInit(): void {
    this.loadProducts();
  }


// Load products from the service
  loadProducts(): void {
    this.productService.getProducts().subscribe({
      next: (data) => {
        this.products = data;
        this.changeDetectorRef.detectChanges();
      },
      error: (error) => {
        console.error('Error loading products:', error);
      }
    });
  }

   // Search products based on the search query
  search(): void {
    if (!this.searchQuery.trim()) {
      this.loadProducts();
      return;
    }
    this.productService.searchProducts(this.searchQuery).subscribe({
      next: (data) => {
        this.products = data;
        this.changeDetectorRef.detectChanges();
      },
      error: (error) => {
        console.error('Error searching products:', error);
      }
    });
  }
}