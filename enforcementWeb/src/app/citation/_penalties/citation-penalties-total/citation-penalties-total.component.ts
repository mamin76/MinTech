import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-citation-penalties-total',
  templateUrl: './citation-penalties-total.component.html',
  styleUrls: ['./citation-penalties-total.component.scss']
})
export class CitationPenaltiesTotalComponent implements OnInit {
  @Input() total: number = 0;

  constructor() { }

  ngOnInit(): void {
  }

}
