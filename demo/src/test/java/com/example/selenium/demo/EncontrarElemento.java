package com.example.selenium.demo;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Sleeper;

public class EncontrarElemento {

	private static WebDriver driver;
	
	static WebElement inputNome; 
	static WebElement inputIdade; 
	static WebElement botaoEnviar; 
	static WebElement botaoLimpar;
	static WebElement divValidos; 
	static WebElement divInvalidos; 

	@BeforeAll
    public static void setup() {
		System.setProperty("webdriver.chrome.driver", "C:\\Ferramentas\\chromedriver.exe");

		driver = new ChromeDriver();
		driver.get("http://localhost:8080/");
		driver.manage().window().maximize();
		
		// Chama o aguarde para o Chrome carregar corretamente
		aguarde();
		
		encontraElementosTela();
	}
	
	private static void encontraElementosTela() {
		inputNome = driver.findElement(By.id("input_nome"));
		inputIdade = driver.findElement(By.id("input_idade"));
		botaoEnviar = driver.findElement(By.id("btn_envio"));
		botaoLimpar = driver.findElement(By.id("btn_reset"));
		
		divValidos = driver.findElement(By.id("dados_validos"));
		divInvalidos = driver.findElement(By.id("dados_invalidos"));
	}

	@Test
	public void testSemIdade() {
		inputNome.sendKeys("Nome");
		botaoEnviar.click();
		
		assertFalse(divValidos.isDisplayed());
		assertTrue(divInvalidos.isDisplayed());
		
		takeScreenshot("testSemIdade");
	}
	
	@Test
	public void testIdadeInvalida() {
		inputNome.sendKeys("Nome");
		inputIdade.sendKeys("abc");
		botaoEnviar.click();
		
		assertFalse(divValidos.isDisplayed());
		assertTrue(divInvalidos.isDisplayed());
		
		takeScreenshot("testIdadeInvalida");
	}
	
	@Test
	public void testSemNome() {
		inputIdade.sendKeys("25");
		botaoEnviar.click();
		
		assertFalse(divValidos.isDisplayed());
		assertTrue(divInvalidos.isDisplayed());
		
		takeScreenshot("testSemNome");
	}
	
	@Test
	public void testBotaoLimpar() {
		inputNome.sendKeys("Nome");
		inputIdade.sendKeys("25");
		botaoLimpar.click();
		
		assertFalse(divValidos.isDisplayed());
		assertFalse(divInvalidos.isDisplayed());
		assertTrue(inputNome.getText().equals(""));
		assertTrue(inputIdade.getText().equals(""));
		
		takeScreenshot("testBotaoLimpar");
	}
	
	@Test
	public void testCadastroCompleto() {
		inputNome.sendKeys("Nome");
		inputIdade.sendKeys("25");
		botaoEnviar.click();
		
		assertTrue(divValidos.isDisplayed());
		assertFalse(divInvalidos.isDisplayed());
		 
		takeScreenshot("testCadastroCompleto");
	}
	
	// Apos cada teste, deve limpar os campos da tela
	@AfterEach
    public void finalize() {
        inputNome.clear();
        inputIdade.clear();
    }
	
	@AfterAll
    public static void tearDown() {
		driver.quit();
    }
	
	public static void takeScreenshot(String fileName){
        File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        Date data = new Date();
        try {
        	File newFile = new File("C:\\Projetos\\demo\\screenshots\\" + fileName + " - " + data.getTime() + ".jpeg");
            FileUtils.copyFile(scrFile, newFile ,true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	private static void aguarde() {
		try {
			Sleeper.SYSTEM_SLEEPER.sleep(Duration.ofMillis(500));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
