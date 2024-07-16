import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { WebServiceWorkerService } from '../../services/web-service-worker.service';

@Component({
  selector: 'app-shell-component',
  templateUrl: './shell-component.component.html',
  styleUrl: './shell-component.component.css'
})
export class ShellComponentComponent implements OnInit, OnDestroy {
 
  isNewAppVersionAvailable: boolean = false;
  newAppUpdateAvailableSubscription!: Subscription;


  constructor(
    private webServiceWorker: WebServiceWorkerService,
  ) { }

  ngOnInit(): void {
    this.checkIfAppUpdated();
  }

  checkIfAppUpdated() {
    this.newAppUpdateAvailableSubscription = this.webServiceWorker.$isAnyNewUpdateAvailable.subscribe((versionAvailableFlag: boolean) => {
      this.isNewAppVersionAvailable = versionAvailableFlag;
    });
  }

  refreshApp() {
    window.location.reload();
  }

  ngOnDestroy() {
    this.newAppUpdateAvailableSubscription?.unsubscribe();
  }
} 