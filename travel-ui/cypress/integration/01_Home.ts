describe('Home page', () => {

    before(() => {
      cy.visit('/')
    })
  
    beforeEach(() => {
      cy.location('pathname').should('eq', '/home')
    })
  
    it('home page contains primary publications', () => {
      cy.get('mdb-carousel')
        .should('be.visible')
    })
  
    it('home page contains secondary publications', () => {
        cy.get('[data-test-id="secondary-publications"]')
        .should('be.visible')
    })

    it('secondary publications contains card elements', () => {
        cy.get('[data-test-id="secondary-publications-element"]')
        .should('be.visible')
        .should('have.length', 3)
        .should('contain.text', "Публикуван на")
    })

    it('header menu contains groups nav', () => {
        cy.get('[data-test-id="groups-nav"]')
        .should('be.visible')
    })

    it('header menu contains groups nav with element for each continent', () => {
        cy.get('[data-test-id="group-item"]')
        .should('be.visible')
        .should('have.length', 7)
        .should('contain.text', "Австралия")
        .should('contain.text', "Азия")
        .should('contain.text', "Европа")
        .should('not.contain.text', "Антарктида")
    })
  })