import { Component, Input, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { InfoStatus } from 'src/app/data/info-status.enum copy';

@Component({
  selector: 'app-info',
  templateUrl: './info.component.html',
  styleUrls: ['./info.component.css']
})
export class InfoComponent implements OnInit {
  @Input() message: InfoStatus;

  constructor(public activeModal: NgbActiveModal) {}

  ngOnInit(): void {}
}
