package org.springaicommunity.mcp.provider.tool;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.modelcontextprotocol.json.McpJsonMapper;
import io.modelcontextprotocol.util.Assert;
import org.springaicommunity.mcp.annotation.McpTool;
import org.springaicommunity.mcp.annotation.McpTool.MetaEntry;

public abstract class AbstractMcpToolProvider {

	protected final List<Object> toolObjects;

	protected McpJsonMapper jsonMapper = McpJsonMapper.createDefault();

	public AbstractMcpToolProvider(List<Object> toolObjects) {
		Assert.notNull(toolObjects, "toolObjects cannot be null");
		this.toolObjects = toolObjects;
	}

	protected Method[] doGetClassMethods(Object bean) {
		return bean.getClass().getDeclaredMethods();
	}

	protected McpTool doGetMcpToolAnnotation(Method method) {
		return method.getAnnotation(McpTool.class);
	}

	protected Class<? extends Throwable> doGetToolCallException() {
		return Exception.class;
	}

	public void setJsonMapper(McpJsonMapper jsonMapper) {
		this.jsonMapper = jsonMapper;
	}

	public McpJsonMapper getJsonMapper() {
		return this.jsonMapper;
	}

	protected Map<String, Object> getToolMeta(McpTool tool) {
		if (tool == null || tool.meta() == null || tool.meta().length == 0) {
			return Collections.emptyMap();
		}

        return Arrays.stream(tool.meta()).collect(Collectors.toMap(MetaEntry::key, MetaEntry::value));
	}
}
