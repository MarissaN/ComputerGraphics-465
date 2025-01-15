package code;

import javax.swing.*;
import static com.jogamp.opengl.GL4.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.util.*;

public class Code extends JFrame implements GLEventListener
{	private GLCanvas myCanvas;
	private int renderingProgram;
	private int vao[] = new int[1];

	private float x = 0.0f;
   private float y = 0.0f;
   private float c = 0.0f;
   private float count = 0.0f;
	private float incX = 0.01f;
   private float incY = 0.01f;
   private float incC = 1.0f;

	public Code()
	{	setTitle("HW 1 - Modifying Triangle Animation");
		setSize(500, 500);
		myCanvas = new GLCanvas();
		myCanvas.addGLEventListener(this);
		this.add(myCanvas);
		this.setVisible(true);
		Animator animator = new Animator(myCanvas);
		animator.start();
	}

	public void display(GLAutoDrawable drawable)
	{	GL4 gl = (GL4) GLContext.getCurrentGL();
      gl.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
		gl.glClear(GL_COLOR_BUFFER_BIT);
		gl.glClear(GL_DEPTH_BUFFER_BIT);
		gl.glUseProgram(renderingProgram);
      
      //First phase; triangles move diagonally up
		x += incX;
      y += incY;
      c = incC;  
      
      //Second phase; triangles move diagonally down
		if (x > 0.75f && x < 1.5f && count == 0.0f) 
      {  incX = +0.01f;
         incY = -0.01f;
         incC = 1.0f;
         count+= 1.0f;
      }
     //Thrid phase; triangles move across
     if (x >= 1.5f && count == 1.0f)
      {  incX = -0.01f;
         incY = -0.0f;
         incC = 0.0f;
         count = 2.0f;
         gl.glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
      }
      //Final phase; set values controlling triangle position back to orginal value
      if (x == -0.1299999f && y == 0.010000015f && count == 2.0f) 
      {  incX = +0.01f;
         incY = +0.01f;
         incC = 1.0f;
         count = 0.0f;
         x = 0.0f;
         y = 0.0f;
         c = 0.0f;
         gl.glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
      } 

		int offsetLocX = gl.glGetUniformLocation(renderingProgram, "offsetX");
		gl.glProgramUniform1f(renderingProgram, offsetLocX, x);
      int offsetLocY = gl.glGetUniformLocation(renderingProgram, "offsetY");
		gl.glProgramUniform1f(renderingProgram, offsetLocY, y);
      int offsetLocC = gl.glGetUniformLocation(renderingProgram, "offsetC");
		gl.glProgramUniform1f(renderingProgram, offsetLocC, c);

		gl.glDrawArrays(GL_TRIANGLES,0,6);
	}

	public void init(GLAutoDrawable drawable)
	{	GL4 gl = (GL4) GLContext.getCurrentGL();
		renderingProgram = Utils.createShaderProgram("code/vertShader.glsl", "code/fragShader.glsl");
		gl.glGenVertexArrays(vao.length, vao, 0);
		gl.glBindVertexArray(vao[0]);
	}

	public static void main(String[] args) { new Code(); }
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {}
	public void dispose(GLAutoDrawable drawable) {}
}