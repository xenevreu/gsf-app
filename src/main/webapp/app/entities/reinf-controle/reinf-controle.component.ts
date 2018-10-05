import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IReinfControle } from 'app/shared/model/reinf-controle.model';
import { Principal } from 'app/core';
import { ReinfControleService } from './reinf-controle.service';

@Component({
    selector: 'jhi-reinf-controle',
    templateUrl: './reinf-controle.component.html'
})
export class ReinfControleComponent implements OnInit, OnDestroy {
    reinfControles: IReinfControle[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private reinfControleService: ReinfControleService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.reinfControleService.query().subscribe(
            (res: HttpResponse<IReinfControle[]>) => {
                this.reinfControles = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInReinfControles();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IReinfControle) {
        return item.id;
    }

    registerChangeInReinfControles() {
        this.eventSubscriber = this.eventManager.subscribe('reinfControleListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
