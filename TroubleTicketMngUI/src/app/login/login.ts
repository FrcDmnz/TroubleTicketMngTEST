import { Component, AfterViewInit, NgZone } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../_services/auth';
import { StorageService } from '../_services/storage';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class Login implements AfterViewInit {
  form: any = { username: '', password: '' };
  isLoginFailed = false;
  errorMessage = '';
  
  // Your Google Site Key
  private readonly siteKey = '6LdaQa0sAAAAABueegSzmd5-BjllqTbz-6emcbUJ';

  constructor(
    private authService: AuthService, 
    private storageService: StorageService,
    private router: Router,
    private ngZone: NgZone
  ) {}

  /**
   * Triggers reCAPTCHA rendering once the view is ready.
   */
  ngAfterViewInit(): void {
    this.initCaptcha();
  }

  /**
   * Polls for grecaptcha availability and renders the widget.
   */
  private initCaptcha(): void {
    const checkInterval = setInterval(() => {
      // @ts-ignore
      if (typeof grecaptcha !== 'undefined' && grecaptcha.render) {
        clearInterval(checkInterval);
        this.ngZone.runOutsideAngular(() => {
          // @ts-ignore
          grecaptcha.render('recaptcha-container', {
            'sitekey': this.siteKey,
            'theme': 'light'
          });
        });
      }
    }, 300);
  }

  /**
   * Main login submission logic.
   */
  onSubmit(): void {
    // @ts-ignore
    const captchaToken = grecaptcha.getResponse();

    if (!captchaToken) {
      this.isLoginFailed = true;
      this.errorMessage = "Robot Test Failed. Try Again.";
      return;
    }

    const { username, password } = this.form;

    this.authService.login(username, password).subscribe({
      next: (data) => {
        this.storageService.saveUser(data);
        this.isLoginFailed = false;
        this.router.navigate(['/home']); 
      },
      error: (err) => {
        this.isLoginFailed = true;
        this.errorMessage = err.error?.message || "Login failed. Check your credentials.";
        // Reset captcha to allow a fresh attempt
        // @ts-ignore
        grecaptcha.reset();
      }
    });
  }
}