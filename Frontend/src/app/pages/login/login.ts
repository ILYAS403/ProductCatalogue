import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { LoginRequest } from '../../models/product';
import { LoginService } from '../../services/login-service';
import { Router } from '@angular/router';

@Component({
  imports: [FormsModule],
  selector: 'app-login',
  styleUrl: './login.css',
  templateUrl: './login.html',
})
export class Login {

    public errorMessage: string = '';
    private loginService: LoginService;

  constructor(loginService: LoginService, private router: Router) {
    this.loginService = loginService;
    
  }

    

 public loginRequest: LoginRequest = {
  username: '',
  password: ''
};



login(): void {

  this.loginService.login(this.loginRequest).subscribe({
    next: (token) => {
      console.log('JWT reçu :', token);

      localStorage.setItem('token', token);

      this.router.navigate(['/'])
    },

    error: (error) => {
      console.error('Erreur de connexion :', error);

      this.errorMessage = 'Username ou mot de passe incorrect.';
    }
  });

}

}
