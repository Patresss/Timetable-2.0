import {AfterViewInit, Component, ContentChild, DoCheck, ElementRef, EventEmitter, forwardRef,
    Input, NgModule, OnChanges, OnInit, Output, SimpleChanges, ViewChild} from '@angular/core';
import {ControlValueAccessor, FormControl, FormsModule, NG_VALIDATORS, NG_VALUE_ACCESSOR, Validator} from '@angular/forms';
import {CommonModule} from '@angular/common';
import {ListItem, MyException} from './multiselect.model';
import {DropdownSettings} from './multiselect.interface';
import {ClickOutsideDirective, ScrollDirective, StyleDirective} from './click-outside';
import {ListFilterPipe} from './list-filter';
import {BadgeComponent, ItemComponent, TemplateRendererComponent} from './menu-item';
import {TimetableSharedModule} from '../../shared/shared.module';

export const DROPDOWN_CONTROL_VALUE_ACCESSOR: any = {
    provide: NG_VALUE_ACCESSOR,
    useExisting: forwardRef(() => AngularMultiSelectComponent),
    multi: true
};
export const DROPDOWN_CONTROL_VALIDATION: any = {
    provide: NG_VALIDATORS,
    useExisting: forwardRef(() => AngularMultiSelectComponent),
    multi: true,
};
const noop = () => {
};

@Component({
    selector: 'jhi-angular2-multiselect',
    templateUrl: './multiselect.component.html',
    /* tslint:disable:use-host-property-decorator */
    host: {'[class]': 'styleClass'},
    styleUrls: ['./multiselect.component.scss'],
    providers: [DROPDOWN_CONTROL_VALUE_ACCESSOR, DROPDOWN_CONTROL_VALIDATION]
})
export class AngularMultiSelectComponent implements OnInit, ControlValueAccessor, OnChanges, Validator, DoCheck, AfterViewInit {

    public static MAX_POSITIVE_HIERARCHY_POINTS = 50.0;
    public static MAX_NEGATIVE_HIERARCHY_POINTS = -50.0;
    public static OPACITY_VALUE = 0.5;

    @Input()
    data: Array<ListItem>;

    @Input()
    blur = true;

    @Input()
    preferenceHierarchy = false;

    @Input()
    styleClass: String = '';

    @Input()
    settings: DropdownSettings;

    @Output('onSelect')
    onSelect: EventEmitter<ListItem> = new EventEmitter<ListItem>();

    @Output('onDeSelect')
    onDeSelect: EventEmitter<ListItem> = new EventEmitter<ListItem>();

    @Output('onSelectAll')
    onSelectAll: EventEmitter<Array<ListItem>> = new EventEmitter<Array<ListItem>>();

    @Output('onDeSelectAll')
    onDeSelectAll: EventEmitter<Array<ListItem>> = new EventEmitter<Array<ListItem>>();

    @Output('onOpen')
    onOpen: EventEmitter<any> = new EventEmitter<any>();

    @Output('onClose')
    onClose: EventEmitter<any> = new EventEmitter<any>();

    @ContentChild(ItemComponent) itemTempl: ItemComponent;
    @ContentChild(BadgeComponent) badgeTempl: BadgeComponent;

    @ViewChild('searchInput') searchInput: ElementRef;

    public selectedItems: Array<ListItem>;
    public isActive: boolean;
    public isSelectAll: boolean;
    public groupedData: Array<ListItem>;
    filter: ListItem = new ListItem();
    public chunkArray: any[];
    public scrollTop: any;
    public chunkIndex: any[] = [];
    public cachedItems: any[] = [];
    public totalRows: any;
    public itemHeight: any = 41.6;
    public screenItemsLen: any;
    public cachedItemsLen: any;
    public totalHeight: any;
    public scroller: any;
    public maxBuffer: any;
    public lastScrolled: any;
    public lastRepaintY: any;

    public isTranslate = true;

    private onTouchedCallback: (_: any) => void = noop;
    private onChangeCallback: (_: any) => void = noop;

    defaultSettings: DropdownSettings = {
        singleSelection: false,
        text: 'Select',
        enableCheckAll: true,
        selectAllText: 'global.select-all',
        unSelectAllText: 'global.un-select-all',
        enableSearchFilter: false,
        maxHeight: 300,
        badgeShowLimit: 999999999999,
        classes: '',
        disabled: false,
        searchPlaceholderText: 'global.search',
        showCheckbox: true,
        noDataLabel: 'global.no-data',
        searchAutofocus: true,
        lazyLoading: false
    };
    public parseError: boolean;

    constructor(public _elementRef: ElementRef) {
    }

    ngOnInit() {
        this.settings = Object.assign(this.defaultSettings, this.settings);
        if (this.settings.groupBy) {
            this.groupedData = this.transformData(this.data, this.settings.groupBy);
        }
        this.totalRows = (this.data && this.data.length);
        this.cachedItems = this.data;
        this.screenItemsLen = Math.ceil(this.settings.maxHeight / this.itemHeight);
        this.cachedItemsLen = this.screenItemsLen * 3;
        this.totalHeight = this.itemHeight * this.totalRows;
        this.maxBuffer = this.screenItemsLen * this.itemHeight;
        this.lastScrolled = 0;
        this.renderChunk(0, this.cachedItemsLen / 2);
    }

    ngOnChanges(changes: SimpleChanges) {
        if (changes.data && !changes.data.firstChange) {
            if (this.settings.groupBy) {
                this.groupedData = this.transformData(this.data, this.settings.groupBy);
                if (this.data.length === 0) {
                    this.selectedItems = [];
                }
            }
        }
        if (changes.settings && !changes.settings.firstChange) {
            this.settings = Object.assign(this.defaultSettings, this.settings);
        }
    }

    ngDoCheck() {
        if (this.selectedItems) {
            if (this.selectedItems.length === 0 || this.data.length === 0 || this.selectedItems.length < this.data.length) {
                this.isSelectAll = false;
            }
        }
    }

    ngAfterViewInit() {
        if (this.settings.lazyLoading) {
            this._elementRef.nativeElement.getElementsByClassName('lazyContainer')[0].addEventListener('jhi-scroll', this.onScroll.bind(this));
        }
    }

    onItemClick(item: ListItem, index: number, evt: Event) {
        if (this.settings.disabled) {
            return false;
        }

        const found = this.isSelected(item);
        const limit = this.selectedItems.length < this.settings.limitSelection ? true : false;

        if (!found) {
            if (this.settings.limitSelection) {
                if (limit) {
                    this.addSelected(item);
                    this.onSelect.emit(item);
                }
            } else {
                this.addSelected(item);
                this.onSelect.emit(item);
            }

        } else {
            this.removeSelected(item);
            this.onDeSelect.emit(item);
        }
        if (this.isSelectAll || this.data.length > this.selectedItems.length) {
            this.isSelectAll = false;
        }
        if (this.data.length === this.selectedItems.length) {
            this.isSelectAll = true;
        }
    }

    public validate(c: FormControl): any {
        return null;
    }

    writeValue(value: any) {
        if (value !== undefined && value !== null) {
            if (this.settings.singleSelection) {
                try {

                    if (value.length > 1) {
                        this.selectedItems = [value[0]];
                        throw new MyException(404, {'msg': 'Single Selection Mode, Selected Items cannot have more than one item.'});
                    } else {
                        this.selectedItems = value;
                    }
                } catch (e) {
                    console.error(e.body.msg);
                }

            } else {
                if (this.settings.limitSelection) {
                    this.selectedItems = value.splice(0, this.settings.limitSelection);
                } else {
                    this.selectedItems = value;
                }
                if (this.selectedItems.length === this.data.length && this.data.length > 0) {
                    this.isSelectAll = true;
                }
            }
        } else {
            this.selectedItems = [];
        }
    }

    // From ControlValueAccessor interface
    registerOnChange(fn: any) {
        this.onChangeCallback = fn;
    }

    // From ControlValueAccessor interface
    registerOnTouched(fn: any) {
        this.onTouchedCallback = fn;
    }

    trackByFn(index: number, item: ListItem) {
        return item.id;
    }

    isSelected(clickedItem: ListItem) {
        let found = false;
        if (this.selectedItems) {
            this.selectedItems.forEach((item) => {
                if (clickedItem.id === item.id) {
                    found = true;
                }
            });
        }
        return found;
    }

    addSelected(item: ListItem) {
        if (this.settings.singleSelection) {
            this.selectedItems = [];
            this.selectedItems.push(item);
            this.closeDropdown();
        } else {
            this.selectedItems.push(item);
        }

        this.onChangeCallback(this.selectedItems);
        this.onTouchedCallback(this.selectedItems);
    }

    removeSelected(clickedItem: ListItem) {
        this.selectedItems.forEach((item) => {
            if (clickedItem.id === item.id) {
                this.selectedItems.splice(this.selectedItems.indexOf(item), 1);
            }
        });
        this.onChangeCallback(this.selectedItems);
        this.onTouchedCallback(this.selectedItems);
    }

    toggleDropdown(evt: any) {
        if (this.settings.disabled) {
            return false;
        }
        this.isActive = !this.isActive;
        if (this.isActive) {
            if (this.settings.searchAutofocus && this.settings.enableSearchFilter) {
                setTimeout(() => {
                    this.searchInput.nativeElement.focus();
                }, 0);
            }
            this.onOpen.emit(true);
        } else {
            this.onClose.emit(false);
        }
        evt.preventDefault();
    }

    closeDropdown() {
        this.filter = new ListItem();
        this.isActive = false;
        this.onClose.emit(false);
    }

    toggleSelectAll() {
        if (!this.isSelectAll) {
            this.selectedItems = [];
            this.selectedItems = this.data.slice();
            this.isSelectAll = true;
            this.onChangeCallback(this.selectedItems);
            this.onTouchedCallback(this.selectedItems);

            this.onSelectAll.emit(this.selectedItems);
        } else {
            this.selectedItems = [];
            this.isSelectAll = false;
            this.onChangeCallback(this.selectedItems);
            this.onTouchedCallback(this.selectedItems);

            this.onDeSelectAll.emit(this.selectedItems);
        }
    }

    transformData(arr: Array<ListItem>, field: any): Array<ListItem> {
        const groupedObj: any = arr.reduce((prev: any, cur: any) => {
            if (!prev[cur[field]]) {
                prev[cur[field]] = [cur];
            } else {
                prev[cur[field]].push(cur);
            }
            return prev;
        }, {});
        const tempArr: any = [];
        Object.keys(groupedObj).map(function(x) {
            tempArr.push({key: x, value: groupedObj[x]});
        });
        return tempArr;
    }

    renderChunk(fromPos: any, howMany: any) {
        this.chunkArray = [];
        this.chunkIndex = [];
        let finalItem = fromPos + howMany;
        if (finalItem > this.totalRows) {
            finalItem = this.totalRows;
        }

        for (let i = fromPos; i < finalItem; i++) {
            this.chunkIndex.push((i * this.itemHeight) + 'px');
            this.chunkArray.push(this.data[i]);
        }
    }

    listFilter() {
    }

    public onScroll(e: any) {
        this.scrollTop = e.target.scrollTop;
        this.updateView(this.scrollTop);

    }

    public updateView(scrollTop: any) {
        const scrollPos = scrollTop ? scrollTop : 0;
        let first = (scrollPos / this.itemHeight) - this.screenItemsLen;
        const firstTemp = '' + first;
        first = parseInt(firstTemp, 10) < 0 ? 0 : parseInt(firstTemp, 10);
        this.renderChunk(first, this.cachedItemsLen);
        this.lastRepaintY = scrollPos;
    }

    public filterInfiniteList(evt: any) {
        const filteredElems: Array<any> = [];
        this.data = this.cachedItems.slice();
        if (evt.target.value.toString() !== '') {
            this.data.filter(function(el: any) {
                for (const prop in el) {
                    if (el[prop].toString().toLowerCase().indexOf(evt.target.value.toString().toLowerCase()) >= 0) {
                        filteredElems.push(el);
                    }
                }
            });
            // this.cachedItems = this.data;
            this.totalHeight = this.itemHeight * filteredElems.length;
            this.totalRows = filteredElems.length;
            this.data = [];
            this.data = filteredElems;
            this.updateView(this.scrollTop);
        } else if (evt.target.value.toString() === '' && this.cachedItems.length > 0) {
            this.data = [];
            this.data = this.cachedItems;
            this.totalHeight = this.itemHeight * this.data.length;
            this.totalRows = this.data.length;
            this.updateView(this.scrollTop);
        }
    }

    getOptionHierarchyStyle(item: any) {
        let color = 'transparent';
        if (this.preferenceHierarchy && item.preferenceHierarchy) {
            if (item.preferenceHierarchy.points > 0) {
                const colorAlpha = item.preferenceHierarchy.points / AngularMultiSelectComponent.MAX_POSITIVE_HIERARCHY_POINTS;
                color = 'rgba(40, 167, 69, ' + colorAlpha + ')';
                return {'border-left': '15px solid ' + color};
            } else if (item.preferenceHierarchy.points < AngularMultiSelectComponent.MAX_NEGATIVE_HIERARCHY_POINTS ) {
                color = 'rgb(220, 53, 69)';
                return {'border-left': '15px solid ' + color};
            } else if (item.preferenceHierarchy.points < 0) {
                const colorAlpha = item.preferenceHierarchy.points / AngularMultiSelectComponent.MAX_NEGATIVE_HIERARCHY_POINTS;
                color = 'rgba(255, 174, 66, ' + colorAlpha + ')';
                return {'border-left': '15px solid ' + color};
            }
        }
        return {'border-left': '15px solid ' + color};
    }

    getSelectedHierarchyStyle() {
        if (this.preferenceHierarchy && this.settings.singleSelection) {
            if (this.selectedItems && this.selectedItems[0] && this.selectedItems[0].preferenceHierarchy) {
                const item = this.selectedItems[0];
                let color = 'transparent';
                if (item.preferenceHierarchy.points > 0) {
                    color = 'rgba(40, 167, 69, ' + item.preferenceHierarchy.points / AngularMultiSelectComponent.MAX_POSITIVE_HIERARCHY_POINTS + ')';
                } else if (item.preferenceHierarchy.points < AngularMultiSelectComponent.MAX_NEGATIVE_HIERARCHY_POINTS ) {
                    color = 'rgb(220, 53, 69)';
                } else if (item.preferenceHierarchy.points < 0) {
                    color = 'rgba(255, 174, 66, ' + item.preferenceHierarchy.points / AngularMultiSelectComponent.MAX_NEGATIVE_HIERARCHY_POINTS + ')';
                }

                return {'border-left': '10px solid ' + color};
            }
        }
        return {};
    }
}

@NgModule({
    imports: [CommonModule, FormsModule, TimetableSharedModule],
    declarations: [AngularMultiSelectComponent, ClickOutsideDirective, ScrollDirective, StyleDirective, ListFilterPipe, ItemComponent, TemplateRendererComponent, BadgeComponent],
    exports: [AngularMultiSelectComponent, ClickOutsideDirective, ScrollDirective, StyleDirective, ListFilterPipe, ItemComponent, TemplateRendererComponent, BadgeComponent]
})
export class AngularMultiSelectModule {
}
