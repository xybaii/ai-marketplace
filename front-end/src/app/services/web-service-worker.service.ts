import { Injectable, OnDestroy } from '@angular/core';
import { SwUpdate } from '@angular/service-worker';
import { BehaviorSubject, interval, Subscription } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class WebServiceWorkerService implements OnDestroy {
  $isAnyNewUpdateAvailable: BehaviorSubject<boolean> = new BehaviorSubject(false);
  serviceSubscriptions: Subscription[] = [];

  constructor(public swUpdate: SwUpdate) {
    this.initialize();
  }

  initialize() {
    if (this.swUpdate.isEnabled) {
      this.serviceSubscriptions.push(interval(15 * 1000).subscribe(() => this.swUpdate.checkForUpdate()));
      this.serviceSubscriptions.push(
        this.swUpdate.versionUpdates.subscribe(evt => {
          if (evt.type === 'VERSION_READY') {
            this.$isAnyNewUpdateAvailable.next(true);
          }
        }),
      );
      this.serviceSubscriptions.push(
        this.swUpdate.unrecoverable.subscribe(evt => {
          window.location.reload();
        }),
      );
    }
  }

  ngOnDestroy(): void {
    this.serviceSubscriptions?.forEach(x => x?.unsubscribe());
  }
}