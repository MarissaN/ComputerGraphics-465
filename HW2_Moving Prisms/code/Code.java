package code;

import java.nio.*;
import javax.swing.*;
import java.lang.Math;
import static com.jogamp.opengl.GL4.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.util.*;
import com.jogamp.common.nio.Buffers;

import org.joml.*;

public class Code extends JFrame implements GLEventListener
{	private GLCanvas myCanvas;
	private int renderingProgram;
	private int vao[] = new int[1];
	private int vbo[] = new int[2];
	private float cameraX, cameraY, cameraZ;
	private float cubeLocX, cubeLocY, cubeLocZ;

	// allocate variables for display() function
	private FloatBuffer vals = Buffers.newDirectFloatBuffer(16);  // buffer for transfering matrix to uniform
	private Matrix4f pMat = new Matrix4f();  // perspective matrix
	private Matrix4f vMat = new Matrix4f();  // view matrix
	private Matrix4f mMat = new Matrix4f();  // model matrix
	private Matrix4f mvMat = new Matrix4f(); // model-view matrix
	private int mvLoc, pLoc;
	private float aspect;
	private double tf;
	private double startTime;
	private double elapsedTime;

	public Code()
	{	setTitle("HW 2 - Moving prisms");
		setSize(600, 600);
		myCanvas = new GLCanvas();
		myCanvas.addGLEventListener(this);
		this.add(myCanvas);
		this.setVisible(true);
		Animator animator = new Animator(myCanvas);
		animator.start();
	}

	public void display(GLAutoDrawable drawable)
	{	GL4 gl = (GL4) GLContext.getCurrentGL();
		gl.glClear(GL_DEPTH_BUFFER_BIT);
		gl.glClear(GL_COLOR_BUFFER_BIT);

		gl.glUseProgram(renderingProgram);
      
		mvLoc = gl.glGetUniformLocation(renderingProgram, "mv_matrix");
		pLoc = gl.glGetUniformLocation(renderingProgram, "p_matrix");

		aspect = (float) myCanvas.getWidth() / (float) myCanvas.getHeight();
		pMat.setPerspective((float) Math.toRadians(60.0f), aspect, 0.1f, 1000.0f);

		vMat.translation(-cameraX, -cameraY, -cameraZ);

		elapsedTime = System.currentTimeMillis() - startTime;
		tf = elapsedTime/1050.0;  // time factor
		mMat.identity();
      
      //prism 1
      
   //Rotate back and forth
    mMat.rotateXYZ(0.0f, (float)Math.sin(0.5*tf)*1.5f, 0.0f);   


  //Move Mouth
   mMat.translate(0.0f, 0.0f, -1.0f);
   mMat.rotateXYZ(-((float)Math.sin(1.3*tf)*0.5f+0.5f), 0.0f, 0.0f);
   mMat.translate(0.0f, 1.0f, 1.0f);
   
   //Rotate the prism to face right direction
    mMat.rotateXYZ(0.0f, 4.71f, 0.0f);
   
     mvMat.identity();                         
		mvMat.mul(vMat);
		mvMat.mul(mMat);

		gl.glUniformMatrix4fv(mvLoc, 1, false, mvMat.get(vals));
		gl.glUniformMatrix4fv(pLoc, 1, false, pMat.get(vals));

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[0]);
		gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(0);

		gl.glEnable(GL_DEPTH_TEST);
		gl.glDepthFunc(GL_LEQUAL);

		gl.glDrawArrays(GL_TRIANGLES, 0, 24);
      
      //Prism 2
      mMat.identity();
     
     //Rotate back and forth
     mMat.rotateXYZ(0.0f, (float)Math.sin(0.5*tf)*1.5f, 0.0f); 
     
    //Move Mouth
    mMat.translate(0.0f, 0.0f, -1.0f);
    mMat.rotateXYZ((float)Math.sin(1.3*tf)*0.5f+0.5f, 0.0f, 0.0f);
    mMat.translate(0.0f, 0.0f, 1.0f);
   
   //Rotate the prism to face right direction
    mMat.rotateXYZ(0.0f, 4.71f, 0.0f);
   
    //Flip bottom prism and reshape it  
    mMat.rotateXYZ(0.0f,3.14f, 0.0f); //Flip prism
    mMat.translate(0.0f, -0.49f, 0.0f);  // Translate along Y-axis 
    mMat.scale(-1.0f,-0.5f,-1.0f); //Smush the prism top to bottom
 
	
		mvMat.identity();
		mvMat.mul(vMat);
		mvMat.mul(mMat);

		gl.glUniformMatrix4fv(mvLoc, 1, false, mvMat.get(vals));
		gl.glUniformMatrix4fv(pLoc, 1, false, pMat.get(vals));

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[0]);
		gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(0);

		gl.glEnable(GL_DEPTH_TEST);
		gl.glDepthFunc(GL_LEQUAL);

	   gl.glDrawArrays(GL_TRIANGLES, 0, 24);
	}

	public void init(GLAutoDrawable drawable)
	{	GL4 gl = (GL4) drawable.getGL();
		startTime = System.currentTimeMillis();
		renderingProgram = Utils.createShaderProgram("code/vertShader.glsl", "code/fragShader.glsl");
		setupVertices();
		cameraX = 0.0f; cameraY = 0.0f; cameraZ = 8.0f;
		cubeLocX = 0.0f; cubeLocY = -2.0f; cubeLocZ = 0.0f;
  

	}

	private void setupVertices()
	{	GL4 gl = (GL4) GLContext.getCurrentGL();
		float[] vertexPositions =
      { 	 -1.0f, -1.0f,  1.0f,  1.0f, -1.0f,  1.0f,  1.0f, -1.0f, -1.0f, //Bottom
			1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f,  1.0f,

      -1.0f, -1.0f, 1.0f, 1.0f, -1.0f, 1.0f, 0.0f, 1.0f, 1.0f,  //Front tiangle
           
       1.0f, -1.0f, -1.0f, 1.0f, -1.0f,  1.0f, 0.0f,  1.0f, -1.0f, //Right side
			1.0f, -1.0f,  1.0f, 0f,  1.0f,  1.0f, 0f,  1.0f, -1.0f,
                     
     -1.0f, -1.0f,  1.0f, -1.0f, -1.0f, -1.0f, 0f,  1.0f,  1.0f, //Left Side
			-1.0f, -1.0f, -1.0f, 0f,  1.0f, -1.0f, 0f,  1.0f,  1.0f,
         
      1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, 0.0f, 1.0f, -1.0f,  //Back triangle 
   };


		gl.glGenVertexArrays(vao.length, vao, 0);
		gl.glBindVertexArray(vao[0]);
		gl.glGenBuffers(vbo.length, vbo, 0);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[0]);
		FloatBuffer vertBuf = Buffers.newDirectFloatBuffer(vertexPositions);
		gl.glBufferData(GL_ARRAY_BUFFER, vertBuf.limit()*4, vertBuf, GL_STATIC_DRAW);
           
	}

	public static void main(String[] args) { new Code(); }
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {}
	public void dispose(GLAutoDrawable drawable) {}
}