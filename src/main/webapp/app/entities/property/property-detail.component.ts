import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { Property } from './property.model';
import { PropertyService } from './property.service';

@Component({
    selector: 'jhi-property-detail',
    templateUrl: './property-detail.component.html'
})
export class PropertyDetailComponent implements OnInit, OnDestroy {

    property: Property;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private propertyService: PropertyService,
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
        this.propertyService.find(id).subscribe((property) => {
            this.property = property;
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
            'propertyListModification',
            (response) => this.load(this.property.id)
        );
    }
}
