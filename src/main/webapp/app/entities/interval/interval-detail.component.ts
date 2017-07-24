import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { Interval } from './interval.model';
import { IntervalService } from './interval.service';

@Component({
    selector: 'jhi-interval-detail',
    templateUrl: './interval-detail.component.html'
})
export class IntervalDetailComponent implements OnInit, OnDestroy {

    interval: Interval;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private intervalService: IntervalService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInIntervals();
    }

    load(id) {
        this.intervalService.find(id).subscribe((interval) => {
            this.interval = interval;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInIntervals() {
        this.eventSubscriber = this.eventManager.subscribe(
            'intervalListModification',
            (response) => this.load(this.interval.id)
        );
    }
}
