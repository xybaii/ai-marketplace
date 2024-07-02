import { Component, ElementRef, Renderer2 } from '@angular/core';
import { trigger, state, style, animate, transition } from '@angular/animations';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css',
  
})
export class NavbarComponent {
  showMenu = false;
  toggleNavbar(){
    this.showMenu = !this.showMenu;
  }
}
