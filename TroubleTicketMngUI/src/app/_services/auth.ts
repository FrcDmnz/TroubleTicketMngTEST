import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

const AUTH_API = 'http://localhost:8080/api/auth/';

/**
 * Service responsible for making HTTP calls to the backend authentication API.
 * It handles login requests and communicates with the Spring Boot AuthController.
 */
@Injectable({
  providedIn: 'root'
})
export class AuthService {
  
  // Define standard headers for JSON content
  private httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) {}

  /**
   * Sends user credentials to the backend for authentication.
   * @param username The user's unique identifier.
   * @param password The user's plain-text password.
   * @returns An Observable containing the JwtResponse from the server.
   */
  login(username: string, password: string): Observable<any> {
    return this.http.post(
      AUTH_API + 'login', 
      { username, password }, 
      this.httpOptions
    );
  }
}