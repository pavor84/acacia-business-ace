# Introduction #
Next version of our application should be:

  1. OSGi based
  1. Has Distributed OSGi communication with the server
  1. Has Single Sign On
  1. Client has bundles for Authorization with Username/Password, Certificates, e.t.c.


# Details #

This is attempt to identify base components which **Good Framework** should have.

## Principles ##
We used bellow principles when identifying components a **Good Framework** need to have:
  1. Should help developer to free his mind from low level technology coding and help him to concentrate on implementing business processes.
  1. Should use best **Design** and **Coding** practices in the java world.
  1. Should be modular based and allow dynamic update of modules. Real world is dynamic and application must be as close as possible to the real world.

## Main layers of application ##
We strongly believe that software systems must be build in layers. Layers help to keep responsibility for specific functionality in one place and help to decrease duplication of code. Layers which we identified:

  1. Domain Model Layer
  1. Persistence Layer
  1. Business Logic Layer
  1. Communication Layer