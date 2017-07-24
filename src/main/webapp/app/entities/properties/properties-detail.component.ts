import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { Properties } from './properties.model';
import { PropertiesService } from './properties.service';

@Component({
    selector: 'jhi-properties-detail',
    templateUrl: './properties-detail.component.html'
})
export class PropertiesDetailComponent implements OnInit, OnDestroy {

    properties: Properties;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private propertiesService: PropertiesService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInProperties();
    }

    load(id) {
        this.propertiesService.find(id).subscribe((properties) => {
            this.properties = properties;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInProperties() {
        this.eventSubscriber = this.eventManager.subscribe(
            'propertiesListModification',
            (response) => this.load(this.properties.id)
        );
    }
}
