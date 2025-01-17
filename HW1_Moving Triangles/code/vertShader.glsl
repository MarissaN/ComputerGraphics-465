#version 430

uniform float offsetX;
uniform float offsetY;


void main(void)
 { if (gl_VertexID == 0) gl_Position = vec4( 1.0 -offsetX,-1.0 +offsetY, 0.0, 1.0);
  else if (gl_VertexID == 1) gl_Position = vec4(0.5 -offsetX,-1.0 +offsetY, 0.0, 1.0);
  else if (gl_VertexID == 2) gl_Position = vec4( 1.0 -offsetX, -0.5 +offsetY, 0.0, 1.0);
  else if (gl_VertexID == 3) gl_Position = vec4( -1.0 +offsetX, 1.0 -offsetY, 0.0, 1.0);
  else if (gl_VertexID == 4) gl_Position = vec4(-0.5 +offsetX, 1.0 -offsetY, 0.0, 1.0);
  else gl_Position = vec4( -1.0 +offsetX, 0.5 -offsetY, 0.0, 1.0);
}
