#version 430
out vec4 color;
uniform float offsetC;
void main(void)
{
	color = vec4(0.0+offsetC, 0.0, 1.0-offsetC, 1.0);
}