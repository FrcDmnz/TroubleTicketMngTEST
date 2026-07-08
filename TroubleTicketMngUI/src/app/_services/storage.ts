import { Injectable } from '@angular/core';

const USER_KEY = 'auth-user';

/**
 * Service responsible for managing user data persistence within the browser.
 * It uses SessionStorage to store the authenticated user's profile and JWT.
 */
@Injectable({
  providedIn: 'root'
})
export class StorageService {

  /**
   * Clears all data from the session storage, effectively logging out the user locally.
   */
  clean(): void {
    window.sessionStorage.clear();
  }

  /**
   * Saves the user object to session storage. 
   * It removes any existing user data before saving the new one.
   * @param user The user profile and token received from the backend.
   */
  public saveUser(user: any): void {
    window.sessionStorage.removeItem(USER_KEY);
    window.sessionStorage.setItem(USER_KEY, JSON.stringify(user));
  }

  /**
   * Retrieves the saved user object from session storage.
   * @returns The parsed user object if found, otherwise null.
   */
  public getUser(): any {
    const user = window.sessionStorage.getItem(USER_KEY);
    return user ? JSON.parse(user) : null;
  }

  /**
   * Checks if a user is currently authenticated by verifying the presence of the user key.
   * @returns True if the user key exists in storage, false otherwise.
   */
  public isLoggedIn(): boolean {
    return window.sessionStorage.getItem(USER_KEY) !== null;
  }
}