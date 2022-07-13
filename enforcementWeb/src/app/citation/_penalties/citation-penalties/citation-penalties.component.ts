import { Component, Input, Output, EventEmitter, OnInit, AfterViewChecked } from '@angular/core';

import { FormBuilder, FormControl, FormGroup, Validators, FormArray } from '@angular/forms';

@Component({
  selector: 'app-citation-penalties',
  templateUrl: './citation-penalties.component.html',
  styleUrls: ['./citation-penalties.component.scss']
})
export class CitationPenaltiesComponent implements OnInit {
  @Input() data
  @Input() penActionType = 'control';
  @Input() isHeader = true;
  @Input() isComment = false;
  @Output() penalties = new EventEmitter();
  @Output() total = new EventEmitter();


  citationPenalties

  loading = false;
  submitted = false;

  saveForm: FormGroup;
  count = new FormControl(1, Validators.min(1));

  private _penActionType = 'Settled';

  constructor(private fb: FormBuilder) {
  }

  ngOnInit(): void {
    console.log("this penActionType", this.penActionType);
    switch (this.penActionType) {
      case 'voided':
        this._penActionType = 'Voided';
        break;
    }
    // console.log('citation penalties data=========', this.data);
    const _penaltiesRequestList = this.data.citationPenalties.map((item) => this.initPenalties(item.cit_pen_Id));
    // console.log("_penaltiesRequestList", _penaltiesRequestList);
    this.saveForm = this.fb.group({
      id: [this.data.id, []],
      penaltiesRequestList: this.fb.array(_penaltiesRequestList),
    });
    setTimeout(() => {
      this.onSubmit();
    }, 100);

    this.saveForm.get('penaltiesRequestList').valueChanges.subscribe(val => {
      this.onSubmit();
    });

  }

  get f() { return this.saveForm.controls; }

  onSubmit() {
    const penaltiesRequestList = this.f.penaltiesRequestList.value;

    const data = {
      "citationRequests": [
        {
          "id": this.f.id.value,
          penaltiesRequestList
        }
      ]
    };
    this.penalties.emit(data);
    this.total.emit(this.getTotal());
  }

  initPenalties(cit_pen_Id) {
    // initialize our address
    return this.fb.group({ // make a nested group
      cit_Pen_Id: [cit_pen_Id],
      count: [1, Validators.min(1)],
      penActionType: [this._penActionType],
      comment: [''],
    });
  }

  getTotal() {
    const penaltiesRequestList = this.f.penaltiesRequestList.value;
    let total = 0;
    penaltiesRequestList.map((item) => {
      const penData = this.data.citationPenalties.filter(pen => pen.cit_pen_Id === item.cit_Pen_Id);
      if (item.penActionType == 'Settled')
        total += item.count * penData[0].operationViolationPenalty.penalty.fees;
    });
    return total;
  }
  getPenActionControlValue(i) {
    return this.f.penaltiesRequestList.value[i].penActionType;
  }

  public myError = (controlName: string, errorName: string) => {
    return (this.submitted) ? this.saveForm.controls[controlName].hasError(errorName) : false;
  }
}
