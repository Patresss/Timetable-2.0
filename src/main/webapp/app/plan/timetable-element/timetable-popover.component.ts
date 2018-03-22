import {Component, HostListener, Input, OnInit} from '@angular/core';
import {EventType, Timetable} from '../../entities/timetable';
import {JhiEventManager} from 'ng-jhipster';
import {Subscription} from 'rxjs/Subscription';
import {RoleType} from '../../util/role-type.model';
import {Account, Principal} from '../../shared';

@Component({
    selector: 'jhi-timetable-popover',
    templateUrl: './timetable-popover.html',
    styleUrls: ['./timetable-popover.component.scss'],
})
export class TimetableElementComponent implements OnInit {

    eventSubscriber: Subscription;
    currentAccount: Account;

    EventType = EventType;

    @Input()
    timetable: Timetable;

    @Input()
    timetableDate: Date;

    @Input()
    selectedSchool: any;

    lastPopoverRef: any;

    constructor(private eventManager: JhiEventManager,
                private principal: Principal) {
    }

    ngOnInit() {
        this.loadCurrentAccount();
        this.registerChangeOnUser();
    }

    private loadCurrentAccount() {
        if (this.principal.isAuthenticated()) {
            this.principal.identity().then((account) => {
                this.currentAccount = account;
            });
        }
    }

    @HostListener('document:click', ['$event'])
    clickOutside(event) {
        // If there's a last element-reference AND the click-event target is outside this element
        if (this.lastPopoverRef && !this.lastPopoverRef._elementRef.nativeElement.contains(event.target)) {
            this.lastPopoverRef.close();
            this.lastPopoverRef = null;
        }
    }

    setCurrentPopoverOpen(popReference) {
        // If there's a last element-reference AND the new reference is different
        if (this.lastPopoverRef && this.lastPopoverRef !== popReference) {
            this.lastPopoverRef.close();
        }
        // Registering new popover ref
        this.lastPopoverRef = popReference;
    }

    goToEdit() {
        console.log('goToEdit')
    }

    registerChangeOnUser() {
        this.eventSubscriber = this.eventManager.subscribe(
            'authenticationSuccess',
            (response) => this.loadCurrentAccount()
        );
    }

    canModifyTimetable() {
        if (this.currentAccount) {
            if (this.currentAccount.authorities.indexOf(RoleType.ROLE_ADMIN.toString()) > -1) {
                return true;
            } else if (this.currentAccount.authorities.indexOf(RoleType.ROLE_SCHOOL_ADMIN.toString()) > -1) {
                return this.selectedSchool.users.indexOf(this.currentAccount.id) > -1;
            }
        } else {
            return false;
        }
    }
}
