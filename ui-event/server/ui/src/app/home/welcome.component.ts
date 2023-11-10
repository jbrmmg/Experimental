import { Component } from '@angular/core';
import {WelcomeService} from './welcome.service';

@Component({
  templateUrl: './welcome.component.html',
  styleUrls: ['./welcome.component.css']
})
export class WelcomeComponent {
  textData1: string;
  textData2: string;

  constructor(private readonly _welcomeService: WelcomeService) {
  }
}
