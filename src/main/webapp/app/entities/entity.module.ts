import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { GsfAppReinfControleModule } from './reinf-controle/reinf-controle.module';
import { GsfAppEmpresaModule } from './empresa/empresa.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        GsfAppReinfControleModule,
        GsfAppEmpresaModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GsfAppEntityModule {}
