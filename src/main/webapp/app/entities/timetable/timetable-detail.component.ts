import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { Timetable } from './timetable.model';
import { TimetableService } from './timetable.service';

@Component({
    selector: 'jhi-timetable-detail',
    templateUrl: './timetable-detail.component.html'
})
export class TimetableDetailComponent implements OnInit, OnDestroy {

    timetable: Timetable;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private timetableService: TimetableService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTimetables();
    }

    load(id) {
        this.timetableService.find(id).subscribe((timetable) => {
            this.timetable = timetable;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTimetables() {
        this.eventSubscriber = this.eventManager.subscribe(
            'timetableListModification',
            (response) => this.load(this.timetable.id)
        );
    }
}
