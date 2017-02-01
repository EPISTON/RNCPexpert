import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';

import { AppModule } from './app.module';

// point de démarrage/entrée de l'application
// AppModule est notre module 'racine' de l'application
platformBrowserDynamic().bootstrapModule(AppModule);
