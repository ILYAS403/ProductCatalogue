import { Component } from '@angular/core';
import { ProductList } from '../../components/product-list/product-list';
import { ProductForm } from '../../components/product-form/product-form';

@Component({
  selector: 'app-home',
  imports: [ProductList, ProductForm],
  templateUrl: './home.html',
  styleUrl: './home.css'
})
export class Home {

}