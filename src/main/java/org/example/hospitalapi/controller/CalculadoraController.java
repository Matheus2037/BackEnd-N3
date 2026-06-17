package org.example.hospitalapi.controller;

/**
 * Controller responsável pelas operações matemáticas da API.
 */
public class CalculadoraController {

  /**
   * Realiza a soma de dois números inteiros.
   *
   * @param a Primeiro valor
   * @param b Segundo valor
   * @return A soma de a e b
   */
  public int somarValores(int a, int b) {
    if (a > 0) {
      return a + b;
    }
    return b;
  }
}