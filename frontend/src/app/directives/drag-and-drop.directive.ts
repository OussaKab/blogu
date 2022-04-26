import {Directive, EventEmitter, HostBinding, HostListener, Output} from '@angular/core';

@Directive({
  selector: '[appDragAndDrop]'
})
export class DragAndDropDirective {

  @HostBinding('class.fileover') fileOver: boolean = false;

  @Output() filesDropped = new EventEmitter<File>();


  constructor() {
  }


  @HostListener('dragstart', ['$event'])
  onDragStart(e: any) {
    e.preventDefault();
    e.stopPropagation();
    console.log('dragstart');
  }


  @HostListener('dragover', ['$event'])
  onDragOver(e: any) {
    e.preventDefault();
    e.stopPropagation();
    console.log('dragover');
    this.fileOver = true;
  }


  @HostListener('drop', ['$event'])
  onDrop(e: any) {
    console.log('drop');
    e.preventDefault();
    e.stopPropagation();
    this.fileOver = false;
    const files = e.dataTransfer.files;
    if (files.length > 0) {
      this.filesDropped.emit(files[0]);
    }
  }

  @HostListener('dragend', ['$event'])
  onDragEnd(e: any) {
    console.log(e, 'dragend');
  }

  @HostListener('dragleave', ['$event'])
  onDragLeave(e: any) {
    console.log(e, 'dragleave');
  }

}
