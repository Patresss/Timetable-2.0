import { Directive, Input, HostListener } from '@angular/core';

@Directive({
    selector: '[closePopoverOnClickOutside]'
})
export class ClosePopoverOnClickOutsideDirective {

    active = false;

    @Input('closePopoverOnClickOutside') popover: { close, isOpen };

    @HostListener('document:click', ['$event.target'])
    docClicked(target): Boolean {
        if (!this.popover.isOpen()) {
            return this.active = false;
        }
        // Click that opens popover triggers this. Ignore first click.
        if (!this.active) {
            return this.active = true;
        }

        let cancelClose = false;
        let popoverWindows = document.getElementsByTagName('ngb-popover-window');

        for (let i = 0; i < popoverWindows.length; i++) {
            cancelClose = cancelClose || popoverWindows[i].contains(target);
        }
        if (!cancelClose) {
            this.popover.close();
        }

        // Deactivate if something else closed popover
        this.active = this.popover.isOpen();
    }

}
