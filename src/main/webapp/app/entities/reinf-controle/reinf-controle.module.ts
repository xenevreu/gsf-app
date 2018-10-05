import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GsfAppSharedModule } from 'app/shared';
import {
    ReinfControleComponent,
    ReinfControleDetailComponent,
    ReinfControleUpdateComponent,
    ReinfControleDeletePopupComponent,
    ReinfControleDeleteDialogComponent,
    reinfControleRoute,
    reinfControlePopupRoute
} from './';

const ENTITY_STATES = [...reinfControleRoute, ...reinfControlePopupRoute];

@NgModule({
    imports: [GsfAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ReinfControleComponent,
        ReinfControleDetailComponent,
        ReinfControleUpdateComponent,
        ReinfControleDeleteDialogComponent,
        ReinfControleDeletePopupComponent
    ],
    entryComponents: [
        ReinfControleComponent,
        ReinfControleUpdateComponent,
        ReinfControleDeleteDialogComponent,
        ReinfControleDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GsfAppReinfControleModule {}
