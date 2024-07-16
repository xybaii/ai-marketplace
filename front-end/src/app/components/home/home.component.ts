import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Slider } from '../../models/models';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {

  constructor(private readonly router: Router) {
    setInterval(() => {
      this.nextSlide();
  }, 5000);
  }
  goToProfile() {
    this.router.navigate(['/profile']);
  }

  login(){
    this.router.navigate(['/login']);
  }


    images: string[] = [
        'https://aigc.sgp1.cdn.digitaloceanspaces.com/gpt.jpg',
        'https://aigc.sgp1.cdn.digitaloceanspaces.com/claude.jpg',
        'https://aigc.sgp1.cdn.digitaloceanspaces.com/copilot.jpg',
        'https://aigc.sgp1.cdn.digitaloceanspaces.com/zapier.jpg',
        'https://aigc.sgp1.cdn.digitaloceanspaces.com/jasper.jpg'
    ];

    urls: string[] = [
      'https://graceful-unity-production.up.railway.app/#/product/2',
      'https://graceful-unity-production.up.railway.app/#/product/1',
      'https://graceful-unity-production.up.railway.app/#/product/136',
      'https://graceful-unity-production.up.railway.app/#/product/137',
      'https://graceful-unity-production.up.railway.app/#/product/31'
    ];

   sliders: Slider[] = this.images.map((image, i) => ({
    image: image,
    url: this.urls[i]
  }));
    


  currentIndex = 0;

  nextSlide() {
      this.currentIndex = (this.currentIndex + 1) % this.images.length;
  }

  prevSlide() {
      this.currentIndex = (this.currentIndex - 1 + this.images.length) % this.images.length;
  }
  
}
