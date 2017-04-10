package com.loozb.core.utils;

import com.loozb.model.SysResource;
import org.apache.commons.collections.CollectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * 将资源转成对应的树关系
 * @Author： 龙召碧
 * @Date: Created in 2017-2-10 0:01
 */
public class TreeUtil {
    @SuppressWarnings({ "unchecked" })
    public static List<SysResource> buildListToTree(List<SysResource> dirs) throws InvocationTargetException, IllegalAccessException {
        List<SysResource> roots = TreeUtil.findRoots(dirs);
        List<SysResource> notRoots = (List<SysResource>) CollectionUtils
                .subtract(dirs, roots);
        for (SysResource root : roots) {
            root.setChildren(TreeUtil.findChildren(root, notRoots));
        }
        return roots;
    }

    private static List<SysResource> findRoots(List<SysResource> allSysResources) throws InvocationTargetException, IllegalAccessException {
        List<SysResource> results = new ArrayList<SysResource>();
        for (SysResource resource : allSysResources) {
            boolean isRoot = true;
            for (SysResource comparedOne : allSysResources) {
                if (resource.getPid().longValue() == comparedOne.getId().longValue()) {
                    isRoot = false;
                    break;
                }
            }
            if (isRoot) {
                resource.setLeaf(false);
                results.add(resource);
            }
        }
        return results;
    }

    @SuppressWarnings("unchecked")
    private static List<SysResource> findChildren(SysResource root,
                                                  List<SysResource> allSysResources) {
        List<SysResource> children = new ArrayList<SysResource>();

        for (SysResource comparedOne : allSysResources) {
            if (comparedOne.getPid().longValue() == root.getId().longValue()) {
                children.add(comparedOne);
            }
        }
        List<SysResource> notChildren = (List<SysResource>) CollectionUtils
                .subtract(allSysResources, children);
        for (SysResource child : children) {
            List<SysResource> tmpChildren = findChildren(child, notChildren);
            if (tmpChildren == null || tmpChildren.size() < 1) {
                // 叶子节点，如果needRoot为false，则移除
                child.setLeaf(true);

            } else {
                child.setLeaf(false);
            }
            child.setChildren(tmpChildren);

        }
        return children;
    }
}