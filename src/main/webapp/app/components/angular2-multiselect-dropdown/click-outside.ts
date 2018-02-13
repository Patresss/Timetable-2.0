import {Directive, ElementRef, EventEmitter, HostListener, Input, OnChanges, OnInit, Output} from '@angular/core';

@Directive({
    selector: '[jhiClickOutside]'
})
export class ClickOutsideDirective {

    @Output()
    public jhiClickOutside = new EventEmitter<MouseEvent>();

    constructor(private _elementRef: ElementRef) {
    }

    @HostListener('document:click', ['$event', '$event.target'])
    public onClick(event: MouseEvent, targetElement: HTMLElement): void {
        if (!targetElement) {
            return;
        }

        const clickedInside = this._elementRef.nativeElement.contains(targetElement);
        if (!clickedInside) {
            this.jhiClickOutside.emit(event);
        }
    }
}

@Directive({
    selector: '[jhiScroll]'
})
export class ScrollDirective {

    @Output()
    public jhiScroll = new EventEmitter<MouseEvent>();

    constructor(private _elementRef: ElementRef) {
    }

    @HostListener('jhiScroll', ['$event'])
    public onClick(event: MouseEvent, targetElement: HTMLElement): void {
        this.jhiScroll.emit(event);
    }
}

@Directive({
    selector: '[jhiStyleProp]'
})
export class StyleDirective implements OnInit, OnChanges {

    @Input() jhiStyleProp: string;

    constructor(private el: ElementRef) {

    }

    ngOnInit() {
        this.el.nativeElement.style.top = this.jhiStyleProp;
    }

    ngOnChanges(): void {
        this.el.nativeElement.style.top = this.jhiStyleProp;
    }
}
