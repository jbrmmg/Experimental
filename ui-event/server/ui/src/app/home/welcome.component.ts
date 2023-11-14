import { Component, OnInit, OnDestroy } from '@angular/core';
import { DatePipe } from '@angular/common'
import {Response} from './welcome.response-component'
import {WelcomeService} from './welcome.service';

@Component({
  templateUrl: './welcome.component.html',
  styleUrls: ['./welcome.component.css']
})
export class WelcomeComponent implements OnInit, OnDestroy {
  textData1: string;
  textData2: string;
  source: EventSource;
//  listener : any;

  constructor(private readonly _welcomeService: WelcomeService,
              private datepipe : DatePipe) {
    this.source = new EventSource('int/file-updates?id=cap');
    this.source.addEventListener('message', this.callback.bind(this));
    window.addEventListener('beforeunload', this.handleBeforeUnload.bind(this));
  }

  handleBeforeUnload(event: BeforeUnloadEvent): void {
    // Perform cleanup tasks before the page is unloaded or refreshed
    this.source.removeEventListener('message', this.callback.bind(this))
    this.source.close();
    console.log("Destroy")
    console.log('Performing cleanup before unload');
  }

  callback(e: MessageEvent) : void {
      let response : Response = JSON.parse(e.data);

      this.textData1 = e.data;
      this.textData2 = this.datepipe.transform(response.updateTime,'HH:mm:ss');
//      console.log(response.updateTime);
  }

  connect(): void {
//    this.listener = function (e) {this.textData1 = e.data;}

    this.source = this._welcomeService.fileUpdateSource();
//    this.source.addEventListener('message', e => {
//      this.textData1 = e.data;
//      console.log(e.data)
//    });
    this.source.addEventListener('message', this.callback.bind(this));
  }

  ngOnInit(): void {
    console.log("Init")
  }

  ngOnDestroy() {
//    this.source.removeEventListener('message', this.callback.bind(this))
//    this.source.close();
//    console.log("Destroy")
  }
}
