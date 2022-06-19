import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AgentCentreComponent } from './agent-centre.component';

describe('AgentCentreComponent', () => {
  let component: AgentCentreComponent;
  let fixture: ComponentFixture<AgentCentreComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AgentCentreComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AgentCentreComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
