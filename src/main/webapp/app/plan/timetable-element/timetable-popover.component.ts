import {Component, HostListener, Input, OnInit} from '@angular/core';
import {EventType, Timetable} from '../../entities/timetable';
import {JhiEventManager} from 'ng-jhipster';
import {Subscription} from 'rxjs/Subscription';
import {RoleType} from '../../util/role-type.model';
import {Account, Principal} from '../../shared';
import {ColorType} from '../color-type.';

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
    colorType: ColorType;

    @Input()
    timetable: Timetable;

    @Input()
    timetableDate: Date;

    @Input()
    selectedSchool: any;

    lastPopoverRef: any;

    opacity = 0.6;

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
            console.log(this.currentAccount)
            if (this.currentAccount.authorities.indexOf(RoleType.ROLE_ADMIN.toString()) > -1) {
                return true;
            } else if (this.currentAccount.authorities.indexOf(RoleType.ROLE_SCHOOL_ADMIN.toString()) > -1) {
                return this.selectedSchool.id === this.currentAccount.schoolId;
            } else if (this.currentAccount.authorities.indexOf(RoleType.ROLE_TEACHER.toString()) > -1 && this.timetable.teacherId === this.currentAccount.teacherId) {
                return true;
            }
        } else {
            return false;
        }
    }

    getColorStyle() {
        if (this.timetable && this.timetable.colorBackgroundForSubject) {
            let colorFromServer = '';
            switch (this.colorType) {
                case ColorType.SUBJECT: {
                    colorFromServer = this.timetable.colorBackgroundForSubject;
                    break;
                }
                case ColorType.DIVISION: {
                    colorFromServer = this.timetable.colorBackgroundForDivision;
                    break;
                }
                case ColorType.PLACE: {
                    colorFromServer = this.timetable.colorBackgroundForPlace;
                    break;
                }
                case ColorType.TEACHER: {
                    colorFromServer = this.timetable.colorBackgroundForTeacher;
                    break;
                }
                default: {
                    colorFromServer = this.timetable.colorBackgroundForSubject;
                    break;
                }
            }
            const color = colorFromServer.slice(0, -1) + ', ' + this.opacity + ')';
            return {'background': color};
        } else {
            return {}
        }

    }
}
