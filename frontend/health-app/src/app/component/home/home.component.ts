import { Component } from '@angular/core';
import {NgIf, NgOptimizedImage} from "@angular/common";

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    NgOptimizedImage,
    NgIf
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {
  imageUrl: string | null = null;

  showImage() {
    this.imageUrl = 'https://mytccbuket.s3.amazonaws.com/receita+oftal.jpg';
  }
}
