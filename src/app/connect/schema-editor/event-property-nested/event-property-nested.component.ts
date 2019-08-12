import { Component, DoCheck, EventEmitter, Injectable, Input, OnInit, Output } from '@angular/core';
import { EventProperty } from '../model/EventProperty';
import { EventPropertyNested } from '../model/EventPropertyNested';
import { UUID } from 'angular2-uuid';
import { EventPropertyList } from '../model/EventPropertyList';
// import {DragulaService} from 'ng2-dragula';
// import {DragDropService} from '../drag-drop.service';
// import {WriteJsonService} from '../write-json.service';
import { EventPropertyPrimitive } from '../model/EventPropertyPrimitive';
import { DomainPropertyProbabilityList } from '../model/DomainPropertyProbabilityList';

@Component({
  selector: 'app-event-property-nested',
  templateUrl: './event-property-nested.component.html',
  styleUrls: ['./event-property-nested.component.css']

})

@Injectable()
export class EventPropertyNestedComponent implements OnInit, DoCheck {

  // constructor(private dragulaService: DragulaService) {  }
  constructor() { }

  open = false;

  @Input() eventPropertyNested: EventPropertyNested;
  @Input() index: number;

  @Input()
  isEditable: Boolean;


  @Input() domainPropertyGuesses: DomainPropertyProbabilityList[];
  @Input() domainPropertyGuess: DomainPropertyProbabilityList;

  @Output() delete: EventEmitter<EventProperty> = new EventEmitter<EventProperty>();

  @Output() addNestedProperty: EventEmitter<any> = new EventEmitter<any>();

  ngOnInit() {
    this.eventPropertyNested.propertyNumber = this.index;
  }

  ngDoCheck() {
    this.eventPropertyNested.propertyNumber = this.index;
  }

  private onClickOpen(): void {
    this.open = !this.open;
  }

  private OnClickDeleteProperty(): void {
    this.delete.emit(this.eventPropertyNested);
  }

  public deleteProperty(property): void {
    // const writeJsonService: WriteJsonService = WriteJsonService.getInstance();
    // const dragDropService: DragDropService = DragDropService.getInstance();

    const toDelete: number = this.eventPropertyNested.eventProperties.indexOf(property);
    this.eventPropertyNested.eventProperties.splice(toDelete, 1);

    if (property.label !== undefined) {
      // const path: string = dragDropService.buildPath(property);
      // writeJsonService.remove(path);
    }
  }

  public addPrimitiveProperty(): void {
    console.log('called primitive');

    const uuid: string = UUID.UUID();
    const parent: EventProperty = this.eventPropertyNested;
    this.eventPropertyNested.eventProperties.push(new EventPropertyPrimitive(uuid, parent));
  }

  public emitAddNestedProperty() {
    this.addNestedProperty.emit();
  }

  private getLabel(): string {
    if (typeof this.eventPropertyNested.label !== 'undefined') {
      return this.eventPropertyNested.label;
    } else if (typeof this.eventPropertyNested.runTimeName !== 'undefined') {
      return this.eventPropertyNested.runTimeName;
    } else {
      return 'Nested Property';
    }
  }

  private isEventPropertyPrimitive(instance): boolean {
    return instance instanceof EventPropertyPrimitive;
  }

  private isEventPropertyNested(instance): boolean {
    return instance instanceof EventPropertyNested;
  }

  private isEventPropertyList(instance): boolean {
    return instance instanceof EventPropertyList;
  }
}
