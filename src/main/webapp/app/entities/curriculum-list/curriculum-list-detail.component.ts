import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';
import {CurriculumList} from './curriculum-list.model';
import {CurriculumListService} from './curriculum-list.service';

@Component({
    selector: 'jhi-curriculum-list-detail',
    templateUrl: './curriculum-list-detail.component.html'
})
export class CurriculumListDetailComponent implements OnInit, OnDestroy {

    curriculumList: CurriculumList;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private curriculumListService: CurriculumListService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCurriculumLists();
    }

    load(id) {
        this.curriculumListService.find(id).subscribe((curriculumList) => {
            this.curriculumList = curriculumList;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCurriculumLists() {
        this.eventSubscriber = this.eventManager.subscribe(
            'curriculumListListModification',
            (response) => this.load(this.curriculumList.id)
        );
    }
}
