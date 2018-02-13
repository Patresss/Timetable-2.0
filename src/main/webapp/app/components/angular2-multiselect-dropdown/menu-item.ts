import {Component, ContentChild, EmbeddedViewRef, Input, OnDestroy, OnInit, TemplateRef, ViewContainerRef} from '@angular/core';

@Component({
    selector: 'jhi-c-item',
    template: ``
})

export class ItemComponent {

    @ContentChild(TemplateRef) template: TemplateRef<any>

    constructor() {
    }

}

@Component({
    selector: 'jhi-c-badge',
    template: ``
})

export class BadgeComponent {

    @ContentChild(TemplateRef) template: TemplateRef<any>

    constructor() {
    }

}

@Component({
    selector: 'jhi-c-template-renderer',
    template: ``
})

export class TemplateRendererComponent implements OnInit, OnDestroy {

    @Input() data: any;
    @Input() item: any;
    view: EmbeddedViewRef<any>;

    constructor(public viewContainer: ViewContainerRef) {
    }

    ngOnInit() {
        this.view = this.viewContainer.createEmbeddedView(this.data.template, {
            '\$implicit': this.data,
            'item': this.item
        });
    }

    ngOnDestroy() {
        this.view.destroy();
    }

}
