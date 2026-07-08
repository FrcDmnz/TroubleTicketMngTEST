import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { StorageService } from '../_services/storage';

/**
 * Main landing component after successful authentication.
 * It manages user data display and handles local session termination.
 */
@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './home.html',
  styleUrl: './home.css'
})
export class Home implements OnInit {
  
  /**
   * Holds the authenticated user's profile and JWT.
   */
  currentUser: any;

  constructor(
    private storageService: StorageService, 
    private router: Router
  ) { }

  /**
   * Initializes the component by checking the user's login status.
   * If no session is found, it redirects to the login page.
   */
  ngOnInit(): void {
    if (!this.storageService.isLoggedIn()) {
      this.router.navigate(['/login']);
      return;
    }
    
    // Retrieve user details from the session storage
    this.currentUser = this.storageService.getUser();
  }

  /**
   * Clears the local session and navigates back to the login screen.
   */
  logout(): void {
    this.storageService.clean();
    this.router.navigate(['/login']);
  }
}